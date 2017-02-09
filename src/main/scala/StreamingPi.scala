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
  val total = sc.longAccumulator("total number of samples")

  samples.foreachRDD { rdd =>

    rdd
      // accumulate the total number of samples
      .mapPartitions { p => total.add(p.size); p }
      // filter points inside the unit circle
      .filter { case (x, y) => x*x + y*y < 1 }
      // accumulate the number of them
      .foreachPartition { p => inside.add(p.size) }

    println(s"Estimate of Pi with ${total.sum / M} million samples = ${4.0f * inside.sum / total.sum}")
  }

}}
