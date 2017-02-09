import java.lang.System.err

import com.kakao.cuesheet.CueSheet
import org.apache.spark.rdd.RDD

import scala.util.Random

object MonteCarloPi extends CueSheet {{
  val samples: RDD[(Double, Double)] = sc.parallelize(1 to (1 << 24)).map {
    _ => (Random.nextDouble(), Random.nextDouble())
  }

  val numSamples = samples.count()
  err.println(s"Using $numSamples samples to find the approximate value of pi")

  val insideCircle = samples.filter { case (x, y) => x * x + y * y <= 1 }.count()
  err.println(s"Pi is roughly ${4.0f * insideCircle / numSamples}")
}}
