package com.example.franchisemanagement.data.model

///email" : "ngethe@gmail.com",
//	"name": "Ngeshe Franchises",
//	"franchise_email": "ngethe_fran@gmail.com",
//	"phone": "07219427545",
//	"address" : "Nairobi"
//}

data class AddFranchiseRequest(
    var email: String,
    var name: String,
    var franchise_email: String,
    var phone: String,
    var address: String
)

data class AddFranchiseResponse(
    var status: Boolean,
    var message: String
)