package edu.depaul.email;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

public class emailFinderTests {



    @Test
    @DisplayName("Tests StorageService Constructor creation")
    void testStorageConstruction(){
        StorageService storage = new StorageService();
        assertTrue(storage instanceof StorageService);
    }

    @Test
    @DisplayName("Tests for exception with GOODLINKS storagetype enum")
    void testGoodLinks(){
        StorageService storage = new StorageService();
        Collection<String> res = new ArrayList<String>();
        assertThrows(EmailFinderException.class, () -> storage.storeList(StorageService.StorageType.GOODLINKS, res));
    }

    @Test
    @DisplayName("Tests for exception with BADLINKS storagetype enum")
    void testBadLinks(){
        StorageService storage = new StorageService();
        Collection<String> res = new ArrayList<String>();
        assertThrows(EmailFinderException.class, () -> storage.storeList(StorageService.StorageType.BADLINKS, res));
    }

    @Test
    @DisplayName("Tests for exception with EMAIL storagetype enum")
    void testEmailLinks(){
        StorageService storage = new StorageService();
        Collection<String> res = new ArrayList<String>();
        assertThrows(EmailFinderException.class, () -> storage.storeList(StorageService.StorageType.EMAIL, res));
    }

    // storageService still needs 50% of storeList covered & 100% of addLocation

    @Test
    @Disabled
    @DisplayName("A test for pageCrawler : Warning(Potentially Long)")
    void testPageCrawling() throws IOException {
        String url = "https://jsoup.org/";
        StorageService storage = new StorageService();
        PageCrawler crawler = new PageCrawler(storage, 50);
        crawler.crawl(url);
        crawler.report();
        // .... the majority of my missing coverage is PageCrawler
    }

    @Test
    @DisplayName("A test for pageParser")
    void testPageParsing(){
        String target = "<html><a href='test/resources/testingSM.html'>expectedLink</a></body></html>";
        //String target = "http://docs.oracle.com/javase/7/docs/api/";
        Document doc = Jsoup.parse(target);
        //Document doc = Jsoup.connect(target);
        PageParser parsing = new PageParser();
        parsing.findLinks(doc);
        assertEquals(null, doc.getElementById("expectedLink"));

    }
    // pageParser still needs 100% of findEmails covered
}
