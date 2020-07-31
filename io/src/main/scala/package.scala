package cilib

import com.github.mjakubowski84.parquet4s._
import org.apache.parquet.hadoop.metadata.CompressionCodecName
import scalaz._, Scalaz._
import zio._
import zio.blocking.Blocking
import zio.stream._

package object io {
  import cilib.exec._

  def writeCsvWithHeader[F[_], A](
    file: java.io.File,
    data: F[A]
  )(implicit F: Foldable[F], N: ColumnNameEncoder[A], A: EncodeCsv[A]): Unit = {
    val list = F.toList(data)

    list.headOption match {
      case None =>
        println("No data to persist")

      case Some(o) =>
        val pw = new java.io.PrintWriter(file, "UTF-8")

        pw.println(N.encode(o).mkString(","))

        list.foreach { item =>
          val encoded = EncodeCsv.write(item)
          pw.println(encoded)
        }

        pw.close
    }
  }

  def writeCsv[F[_]: Foldable, A: EncodeCsv](file: java.io.File, data: F[A]): Unit = {
    val list = Foldable[F].toList(data)

    list.headOption match {
      case None =>
        println("No data to persist")

      case Some(_) =>
        val pw = new java.io.PrintWriter(file, "UTF-8")

        list.foreach { item =>
          val encoded = EncodeCsv.write(item)
          pw.println(encoded)
        }

        pw.close
    }
  }

  def csvSink[A: EncodeCsv](
    file: java.io.File
  )(implicit A: EncodeCsv[Measurement[A]]): ZSink[Blocking, Throwable, Measurement[A], Measurement[A], Unit] = {
    val managedChannel = ZManaged.make(
      blocking.effectBlockingInterrupt {
        new java.io.PrintWriter(file)
      }
    )(chan => blocking.effectBlocking(chan.close()).orDie)

    val writer: ZSink[Blocking, Throwable, Measurement[A], Measurement[A], Unit] =
      ZSink.managed(managedChannel) { chan =>
        ZSink.foreach[Blocking, Throwable, Measurement[A]](measurement =>
          blocking.effectBlockingInterrupt {
            chan.println(A.encode(measurement).mkString(","))
          }
        )
      }

    writer
  }

  def csvHeaderSink[A: EncodeCsv](
    file: java.io.File
  )(
    implicit A: EncodeCsv[Measurement[A]],
    N: ColumnNameEncoder[Measurement[A]]
  ): ZSink[Blocking, Throwable, Measurement[A], Measurement[A], Unit] = {
    val managedChannel = ZManaged.make(
      blocking.effectBlockingInterrupt {
        new java.io.PrintWriter(file)
      }
    )(chan => blocking.effectBlocking(chan.close()).orDie)

    val writer: ZSink[Blocking, Throwable, Measurement[A], Measurement[A], Unit] =
      ZSink.managed(managedChannel) { chan =>
        var headerWritten = false

        ZSink.foreach[Blocking, Throwable, Measurement[A]](measurement =>
          blocking.effectBlockingInterrupt {
            if (!headerWritten) {
              chan.println(N.encode(measurement).mkString(","))
              headerWritten = true
            }

            chan.println(A.encode(measurement).mkString(","))
          }
        )
      }

    writer
  }

  def writeParquet[F[_]: Foldable, A: ParquetRecordEncoder: ParquetSchemaResolver](
    file: java.io.File,
    data: F[A]
  )(implicit encoder: ParquetRecordEncoder[Measurement[A]], schema: ParquetSchemaResolver[Measurement[A]]): Unit = {
    val options = ParquetWriter.Options(
      compressionCodecName = CompressionCodecName.SNAPPY,
      pageSize = 4 * 1024 * 1024,
      rowGroupSize = 16 * 1024 * 1024
    )

    ParquetWriter.writeAndClose(file.getAbsolutePath, data.toList, options)
  }

  def parquetSink[A: ParquetRecordEncoder: ParquetSchemaResolver](file: java.io.File)(
    implicit encoder: ParquetRecordEncoder[Measurement[A]],
    schema: ParquetSchemaResolver[Measurement[A]]
  ): ZSink[Blocking, Throwable, Measurement[A], Measurement[A], Unit] = {
    val options = ParquetWriter.Options(
      compressionCodecName = CompressionCodecName.SNAPPY,
      pageSize = 4 * 1024 * 1024,
      rowGroupSize = 16 * 1024 * 1024
    )

    val managedChannel = ZManaged.make(
      blocking.effectBlockingInterrupt {
        ParquetWriter.writer[Measurement[A]](file.getAbsolutePath, options)
      }
    )(chan => blocking.effectBlocking(chan.close()).orDie)

    val writer: ZSink[Blocking, Throwable, Measurement[A], Measurement[A], Unit] =
      ZSink.managed(managedChannel) { chan =>
        ZSink.foreach[Blocking, Throwable, Measurement[A]](measurement =>
          blocking.effectBlockingInterrupt {
            chan.write(measurement)
          }
        )
      }

    writer
  }

}
