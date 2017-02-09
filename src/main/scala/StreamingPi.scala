import com.kakao.cuesheet.CueSheet
import org.apache.spark.streaming.dstream.DStream

import scala.language.postfixOps
import scala.util.Random

object StreamingPi extends CueSheet {{
  val M = 1000000 // million

  val samples: DStream[(Double, Double)] = ssc.schedulingStream().flatMap { time =>
    (1 to M).map { _ => (Random.nextDouble, Random.nextDouble) }
  }

  val inside = sc.longAccumulator("number of samples inside circle")

  samples.foreachRDD { rdd =>
    val count = rdd.filter {
      case (x, y) => x * x + y * y < 1
    }.count()
    println(s"Estimate of Pi with ${inside.count / M} million samples = ${4 * inside.avg}")
  }

}}
