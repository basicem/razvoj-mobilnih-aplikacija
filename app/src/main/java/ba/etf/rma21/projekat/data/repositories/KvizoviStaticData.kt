//package ba.etf.rma21.projekat.data.repositories
//
//import ba.etf.rma21.projekat.data.models.Kviz
//import java.util.*
//
//
//fun sviKvizovi(): List<Kviz> {
//    return listOf(
//        //zelen
//        Kviz(  "Kviz 0",
//            "OE",
//            transformisi(2021,4,4),
//            transformisi(2021,7,20),
//            transformisi(1970,1,1),
//            2,
//            "PON 13:00",
//            null
//        ),
//        //crven
//        Kviz(  "Kviz 1",
//                "OE",
//            transformisi(2021,3,1),
//            transformisi(2021,3,12),
//            transformisi(1970,1,1),
//                5,
//                "SRI 14:30",
//                null
//        ),
//        //zuti
//        Kviz(  "Kviz 0",
//                "DM",
//            transformisi(2021,5,10),
//            transformisi(2021,5,15),
//            transformisi(1970,1,1),
//                5,
//                "PON 15:00",
//                null
//        ),
//        //zelen
//        Kviz(  "Kviz 0",
//            "RPR",
//            transformisi(2021,4,1),
//            transformisi(2021,5,15),
//            transformisi(1970,1,1),
//            2,
//            "CET 12:00",
//            null
//        ),
//        //crven
//        Kviz(  "Kviz 1",
//            "DM",
//            transformisi(2021,3,1),
//            transformisi(2021,3,10),
//            transformisi(1970,1,1),
//            2,
//            "PON 12:00",
//            null
//        ),
//            //plav
//        Kviz(  "Kviz 1",
//                "WT",
//                transformisi(2021,4,1),
//                transformisi(2021,8,5),
//                transformisi(1970,1,1),
//                2,
//                "PET 12:00",
//                null
//        ),
//            //crven
//        Kviz(  "Kviz 1",
//                "RPR",
//                transformisi(2021,4,1),
//                transformisi(2021,4,5),
//                transformisi(1970,1,1),
//                2,
//                "PET 10:30",
//                null
//        )
//    );
//}
//
//fun transformisi(godina: Int, mjesec: Int, dan: Int): Date {
//    val c1 = Calendar.getInstance()
//    c1.set(godina, mjesec - 1, dan)
//    return c1.time
//}
