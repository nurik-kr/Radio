package kg.nurik.radio.data

object Resources {

    fun generate(): ArrayList<RadioStations> {

        val list = arrayListOf<RadioStations>()
        list.add(
            RadioStations(
                "89.0",
                "Europa Plus",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio3_mf_p"
            )
        )
        list.add(
            RadioStations(
                "101.3",
                "Kyrgyz FM",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio4fm_mf_p"
            )
        )
        list.add(
            RadioStations(
                "101.7",
                "Manas FM",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio5live_mf_p"
            )
        )
        list.add(
            RadioStations(
                "102.5",
                "Sanjyra FM",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_6music_mf_p"
            )
        )
        list.add(
            RadioStations(
                "103.3",
                "Radio Ok",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_asianet_mf_p"
            )
        )
        list.add(
            RadioStations(
                "105.6",
                "Hit FM",
                "http://bbcwssc.ic.llnwd.net/stream/bbcwssc_mp1_ws-eieuk"
            )
        )
        return list
    }
}

data class RadioStations(
    val name: String,
    val desc: String,
    val stations: String,
    var isActivated: Boolean = false
)

