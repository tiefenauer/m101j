db.albums.createIndex({'images': 1})
db.albums.getIndexes()
db.images.count({tags: 'sunrises'})
/*
Album:
{
        "_id" : 0,
        "images" : [
                2433,
                2753,
                2983,
                6510,
                11375,
                12974,
                15344,
                16952,
                19722,
                23077,
                24772,
                31401,
                32579,
                32939,
                33434,
                36328,
                39247,
                39892,
                40597,
                45675,
                46147,
                46225,
                48406,
                49947,
                55361,
                57420,
                60101,
                62423,
                64640,
                65000,
                67203,
                68064,
                75918,
                80196,
                80642,
                82848,
                83837,
                84460,
                86419,
                87089,
                88595,
                88904,
                89308,
                91989,
                92411,
                98135,
                98548,
                99334
        ]
}

Image:
{
        "_id" : 0,
        "height" : 480,
        "width" : 640,
        "tags" : [
                "dogs",
                "work"
        ]
}
*/