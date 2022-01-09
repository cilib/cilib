package cilib
package exec

import com.github.mjakubowski84.parquet4s._
import zio.stream._

sealed abstract class Env
final case object Unchanged extends Env
final case object Change    extends Env

object Env {
  def unchanging: UStream[Env] =
    UStream(Unchanged: Env) ++ unchanging

  def frequency[A](n: Long): UStream[Env] =
    unchanging.take(n - 1) ++ UStream(Change: Env) ++ frequency(n)

  implicit val envTypeCodec: RequiredValueCodec[Env] =
    new RequiredValueCodec[Env] {
      //val stringCodec = implicitly[ValueCodec[String]]

      override protected def decodeNonNull(value: Value, configuration: ValueCodecConfiguration): Env =
        value match {
          case BinaryValue(binary) =>
            binary.toStringUsingUTF8 match {
              case "Unchanged" => Unchanged
              case "Change"    => Change
            }
        }

      override protected def encodeNonNull(data: Env, configuration: ValueCodecConfiguration): Value =
        data match {
          case Unchanged => BinaryValue("Unchanged")
          case Change    => BinaryValue("Change")
        }
    }

  import com.github.mjakubowski84.parquet4s.ParquetSchemaResolver.TypedSchemaDef
  import com.github.mjakubowski84.parquet4s.{ Value, ValueCodec }
  import org.apache.parquet.io.api.Binary
  import org.apache.parquet.schema.{ LogicalTypeAnnotation, PrimitiveType }

  implicit val envCodec: ValueCodec[Env] =
    new OptionalValueCodec[Env] {
      override protected def decodeNonNull(value: Value, configuration: ValueCodecConfiguration): Env =
        value match {
          case BinaryValue(binary) =>
            binary.toStringUsingUTF8 match {
              case "Unchanged" => Unchanged
              case "Changed"   => Change
              case name @ _    => throw new IllegalArgumentException(s"Invalid environment type: $name")
            }

          case _ => sys.error("Invalid value found for Env type")
        }
      override protected def encodeNonNull(data: Env, configuration: ValueCodecConfiguration): Value  =
        BinaryValue(Binary.fromString(data match {
          case Unchanged => "Unchanged"
          case Change    => "Change"
        }))

    }

  implicit val envTypeSchema: TypedSchemaDef[Env] = // Save the data as a String in the schema
    SchemaDef
      .primitive(
        primitiveType = PrimitiveType.PrimitiveTypeName.BINARY,
        required = true,
        logicalTypeAnnotation = Some(LogicalTypeAnnotation.stringType())
      )
      .typed[Env]
}
