import com.kakao.cuesheet.CueSheet
import org.apache.spark.rdd.RDD

import scala.util.Random

object CueSheetStarter extends CueSheet {{
  val samples: RDD[Int] = sc.parallelize(1 to (1 << 24))
  val numSamples = samples.count()

  println(s"Using $numSamples samples to find the approximate value of pi")

  val insideCircle = samples.map {
    _ => (Random.nextDouble(), Random.nextDouble())
  }.collect {
    case (x, y) if x * x + y * y <= 1 => true
  }.count()

  println(s"Pi is roughly ${4.0 * insideCircle / numSamples}")
}}
