package com.talk.client.service.entry

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class EntryController {

    @GetMapping("/")
    fun entryMain(): String = "main"
}