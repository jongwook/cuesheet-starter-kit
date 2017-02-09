import java.lang.System.err

import com.kakao.cuesheet.CueSheet
import org.apache.spark.streaming.dstream.DStream

import scala.language.postfixOps
import scala.util.Random

object StreamingPi extends CueSheet {{
  val M = 1000000 // million
  val samples: DStream[(Double, Double)] = ssc.schedulingStream().flatMap { time =>
    (1 to M).map { _ => (Random.nextDouble, Random.nextDouble) }
  }

  var (inside, total) = (0L, 0L)

  samples.foreachRDD { rdd =>
    total += M
    inside += rdd.filter { case (x, y) => x*x + y*y < 1 }.count()
    err.println(s"Estimate of Pi with ${total / M}M samples = ${4.0f * inside / total}")
  }
}}
