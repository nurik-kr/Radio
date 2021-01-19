package kg.nurik.radio.data

object Resources {

    fun generate(): ArrayList<RadioStations> {

        val list = arrayListOf<RadioStations>()
        list.add(
            RadioStations(
                "TUMAR.FM",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio1_mf_p"
            )
        )
        list.add(
            RadioStations(
                "EUROPA PLUS",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio2_mf_p"
            )
        )
        list.add(
            RadioStations(
                "BBC - Radio 3",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio3_mf_p"
            )
        )
        list.add(
            RadioStations(
                "BBC - Radio 4",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio4fm_mf_p"
            )
        )
        list.add(
            RadioStations(
                "BBC - Radio 5",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio5live_mf_p"
            )
        )
        list.add(
            RadioStations(
                "BBC - Radio 6",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_6music_mf_p"
            )
        )
        list.add(
            RadioStations(
                "BBC Radio Asian Network",
                "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_asianet_mf_p"
            )
        )
        list.add(
            RadioStations(
                "BBC World Service",
                "http://bbcwssc.ic.llnwd.net/stream/bbcwssc_mp1_ws-eieuk"
            )
        )
        return list
    }
}

data class RadioStations(
    val name: String,
    val stations: String
)

