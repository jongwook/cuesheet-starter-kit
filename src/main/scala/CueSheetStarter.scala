import com.kakao.cuesheet.CueSheet

object CueSheetStarter extends CueSheet {
  sc.parallelize(1 to 100).map(_ + 1).sum()
}
