package ch.egli.ticketcorner

import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.element

// page_url = https://www.ticketcorner.ch/eventseries/hospitality-backstagefuehrungen-thunerseespiele-3263499/
class ShopPage {
    val cookieButton = element(byXpath("//*[@id=\"cmpwelcomebtnyes\"]/a"))
    val listButton = element(byXpath("//*[@id=\"eventSelectionBox\"]/div/div[1]/div[1]/div[2]/div[1]/div/a[1]"))
    val firstAvailableDate = element(byXpath("//*[@id=\"componentLoader-id1\"]/div[1]/div/article/div/div[1]/div/time[1]"))
}