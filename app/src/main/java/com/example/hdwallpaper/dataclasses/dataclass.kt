package com.example.hdwallpaper.dataclasses
class dataclass {
    var imageURL: String? = null
    var caption: String? = null

    constructor()
    constructor(imageURL: String?, caption: String?) {
        this.imageURL = imageURL
        this.caption = caption
    }
}
