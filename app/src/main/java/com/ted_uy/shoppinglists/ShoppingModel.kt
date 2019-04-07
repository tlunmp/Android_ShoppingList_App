package com.ted_uy.shoppinglists

class ShoppingModel {
    var listID : Int = 0
    var listName: String = ""

    var itemName: String = ""
    var quantity: Int = 0
    var price: Double = 0.0
    var itemID : Int = 0

    constructor(){}

    constructor(itemID: Int) {
        this.itemID = itemID

    }

}

