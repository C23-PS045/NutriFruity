# API NutriFruity

## Endpoint

https://nutrifruity1-qfwlnztfjq-as.a.run.app

## Get All Buah
- URL
  - /buah

- Method
  - GET

- Request Body
  - id as Integer
  - nama_buah as String
  - gambar as String
  - color as String
  - border_color as String

- Example
  - https://nutrifruity1-qfwlnztfjq-as.a.run.app/buah

- Response
  ```
  [
    {
        "id": 1,
        "nama_buah": "Stroberi",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/1.png",
        "color": "0xFFDA573B",
        "border_color": "0xFFBB2E01"
    },
    {
        "id": 2,
        "nama_buah": "Pisang",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/2.png",
        "color": "0xFFE4B639",
        "border_color": "0xFFAE8801"
    },
    {
        "id": 3,
        "nama_buah": "Jeruk",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/3.png",
        "color": "0xFFEB8A31",
        "border_color": "0xFFBB4F01"
    },
    {
        "id": 4,
        "nama_buah": "Alpukat",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/4.png",
        "color": "0xFFA3B56A",
        "border_color": "0xFF6D7947"
    },
    {
        "id": 5,
        "nama_buah": "Apel",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/5.png",
        "color": "0xFFD6563A",
        "border_color": "0xFFBB2E01"
    },
    {
        "id": 6,
        "nama_buah": "Jambu Biji",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/6.png",
        "color": "0xFF9DA34B",
        "border_color": "0xFF717535"
    },
    {
        "id": 7,
        "nama_buah": "Pepaya",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/7.png",
        "color": "0xFFA09D4E",
        "border_color": "0xFF747239"
    },
    {
        "id": 8,
        "nama_buah": "Melon",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/8.png",
        "color": "0xFFBB9F68",
        "border_color": "0xFF8E7951"
    },
    {
        "id": 9,
        "nama_buah": "Anggur",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/9.png",
        "color": "0xFFA3344C",
        "border_color": "0xFF722536"
    },
    {
        "id": 10,
        "nama_buah": "Semangka",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/10.png",
        "color": "0xFF5A6B3C",
        "border_color": "0xFF374125"
    },
    {
        "id": 11,
        "nama_buah": "Mangga",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/11.png",
        "color": "0xFFB1A935",
        "border_color": "0xFF8B8532"
    },
    {
        "id": 12,
        "nama_buah": "Pir",
        "gambar": "https://storage.googleapis.com/image-nutrifruity/12.png",
        "color": "0xFFCBA04F",
        "border_color": "0xFF907238"
    }
  ]
  ```
## Get Buah by Id
- URL
  - /buah/{id}

- Method
  - GET

- Request Body
  - id as Integer
  - nama_buah as String
  - gambar as String
  - buah_id as Integer
  - nutrisi as String
  - manfaat as String

- Example
  - https://nutrifruity1-qfwlnztfjq-as.a.run.app/buah/1

- Response
  ```
  {
    "id": 1,
    "nama_buah": "Stroberi",
    "gambar": "https://storage.googleapis.com/image-nutrifruity/1.png",
    "nutrisi": [
        {
            "id": 1,
            "buah_id": 1,
            "nutrisi": "Karbohidrat"
        },
        {
            "id": 2,
            "buah_id": 1,
            "nutrisi": "Serat"
        },
        {
            "id": 3,
            "buah_id": 1,
            "nutrisi": "Protein"
        },
        {
            "id": 4,
            "buah_id": 1,
            "nutrisi": "Kalsium"
        },
        {
            "id": 5,
            "buah_id": 1,
            "nutrisi": "Folat"
        },
        {
            "id": 6,
            "buah_id": 1,
            "nutrisi": "Vitamin C"
        },
        {
            "id": 7,
            "buah_id": 1,
            "nutrisi": "Vitamin B6"
        },
        {
            "id": 8,
            "buah_id": 1,
            "nutrisi": "Magnesium"
        },
        {
            "id": 9,
            "buah_id": 1,
            "nutrisi": "Kalium"
        },
        {
            "id": 10,
            "buah_id": 1,
            "nutrisi": "Zat besi"
        },
        {
            "id": 11,
            "buah_id": 1,
            "nutrisi": "Antioksidan, seperti antosianin dan ellagitannins"
        }
    ],
    "manfaat": [
        {
            "id": 1,
            "buah_id": 1,
            "manfaat": "Menjaga kesehatan jantung"
        },
        {
            "id": 2,
            "buah_id": 1,
            "manfaat": "Mencegah kanker"
        },
        {
            "id": 3,
            "buah_id": 1,
            "manfaat": "Menjaga kesehatan kulit"
        },
        {
            "id": 4,
            "buah_id": 1,
            "manfaat": "Menjaga kesehatan tulang"
        },
        {
            "id": 5,
            "buah_id": 1,
            "manfaat": "Menurunkan berat badan"
        }
    ]
  }
  ```
