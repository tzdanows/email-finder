package edu.depaul.email;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class emailFinderTests {

    //--------- EmailFinder Tests ---------

    @Test
    //@Disabled
    @DisplayName("Tests for EmailFinder with no links passed in.")
    void noLinksMain(){
        EmailFinder finder = new EmailFinder();
        String[] link = {};
        finder.main(link); // tests main
        assertNotNull(finder);
    }

    @Test
    //@Disabled
    @DisplayName("Tests EmailFinder with link and email")
    void linksMain(){
        String url = "https://tz1873031links.htmlsave.net/";
        EmailFinder finder = new EmailFinder();
        String[] link = {url, "7"};
        finder.run(link);// tests run
        assertNotNull(finder);
    }


    /*--------- ListWriter Tests ---------
        1. construct a ListWriter with a mock OutputStream to verify what ListWriter attempts to write
        2. construct a ListWriter with a ByteArrayOutputStream to see what was actually written without having to read files after. */
    @Test
    //@Disabled
    @DisplayName("Tests ListWriter constructor w/ mock")
    void testLWconstructor() {
        OutputStream output = mock(OutputStream.class);
        ListWriter writer = new ListWriter(output);
        assertNotNull(writer);
    }

    @Test
    //@Disabled
    @DisplayName("Tests whether what ListWriter is writing is the expected result")
    void testLWcontents() {
        ByteArrayOutputStream Boutput = new ByteArrayOutputStream();
        ListWriter writer = new ListWriter(Boutput);

        String res1 = "testing";
        String res2 = "ListWriter";
        String res = res1 + "\n" + res2+ "\n"; // +"\n" necessary due to formatting in comparison
        String retVal = "";
        Collection<String> written = new ArrayList<>();
        written.add(res1);
        written.add(res2);

        try {
            writer.writeList(written);
            retVal = Boutput.toString();
            assertTrue(res.equals(retVal));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*--------- PageCrawler Tests ---------
    1. construct a PageCrawler with a mock StorageService to verify something related to the reporting process*/

    @Test
    //@Disabled
    @DisplayName("Tests PageCrawler with mock & max emails")
    void testPageCrawlerMockMax() {
        StorageService storage = mock(StorageService.class);
        PageCrawler crawler = new PageCrawler(storage, 50);
        assertNotNull(crawler);
    }

    @Test
    //@Disabled
    @DisplayName("Tests PageCrawler with mock & min emails")
    void testPageCrawlerMockMin() {
        StorageService storage = mock(StorageService.class);
        PageCrawler crawler = new PageCrawler(storage, 0);
        assertNotNull(crawler);
    }

    @Test
    @Disabled
    @DisplayName("Tests for pageCrawler crawl()")
    void testPageCrawlerCrawl() throws IOException {
        String url = "https://alextest333.htmlsave.net/";
        StorageService storage = mock(StorageService.class);
        PageCrawler crawler = new PageCrawler(storage);
        crawler.crawl(url);
        crawler.report();
        assertEquals(crawler.getBadLinks().size(),2 );
        assertEquals(crawler.getGoodLinks().size(), 4);
    } // Meant to be parameterized test eventually to check exact good/bad links on site but couldn't get over infinite looping

    @Test
    //@Disabled
    @DisplayName("Tests for PageCrawler getEmails()")
    void testPageCrawlerGet()
    {
        String url = "https://tz1873031links.htmlsave.net/";
        StorageService storage = mock(StorageService.class);
        PageCrawler crawler = new PageCrawler(storage, 5);
        crawler.crawl(url);
        Collection<String> crawled = crawler.getEmails();
        for (String i : crawled)
        {
            assertTrue(i.contains("@"));
        }
    }


            // Pagecrawler is missing pagecrawler(StorageService) & getGoodLinks & getBadLinks from testPageCrawlerCrawl() => not DONE

    //--------- PageFetcher Tests ---------


    @Test
    //@Disabled
    @DisplayName("Tests if html string was returned via getString()")
    void testGetString() {
        String url = "https://tz1873031links.htmlsave.net/";
        String str = null;
        PageFetcher fetcher = new PageFetcher();
        str = fetcher.getString(url);
        assertNotNull(str);
    }

    @Test
    //@Disabled
    @DisplayName("Tests if html doc was returned via get() from url")
    void testGet1() {
        String url = "https://tz1873031links.htmlsave.net/";
        Document doc = null;
        PageFetcher fetcher = new PageFetcher();
        doc = fetcher.get(url);
        assertNotNull(doc);
    }
    @Test
    //@Disabled
    @DisplayName("Tests if html doc was returned via get() from local doc")
    void testGet2() {
        String url = "src/test/resources/testingSM.html";
        Document doc = null;
        PageFetcher fetcher = new PageFetcher();
        doc = fetcher.get(url);
        assertNotNull(doc);
    }

        // full coverage needed from getString() & get() => (exceptions)


    /*--------- PageParser Tests ---------*/

    @Test
    //@Disabled
    @DisplayName("Tests for pageParser url")
    void testPageParsing1(){
        String url = "https://tz1873031links.htmlsave.net/";
        Document doc = Jsoup.parse(url);
        PageParser parsing = new PageParser();
        parsing.findLinks(doc);
        assertEquals(null, doc.getElementById("expectedLink"));

    }
    @Test
    //@Disabled
    @DisplayName("Tests for pageParser file")
    void testPageParsing2() {
        String url = "src/test/resources/testingSM.html";
        Document doc = Jsoup.parse(url);
        PageParser parsing = new PageParser();
        parsing.findLinks(doc);
        assertEquals(null, doc.getElementById("expectedLink"));
    }
        // DONE


    //--------- StorageService Tests ---------

    @Test
    //Disabled
    @DisplayName("Tests StorageService Constructor creation")
    void testStorageConstruction(){
        StorageService storage = new StorageService();
        assertTrue(storage instanceof StorageService);
    }

    @Test
    //Disabled
    @DisplayName("Tests for exception with GOODLINKS storagetype")
    void testGoodLinks(){
        StorageService storage = new StorageService();
        Collection<String> res = new ArrayList<String>();
        assertThrows(EmailFinderException.class, () -> storage.storeList(StorageService.StorageType.GOODLINKS, res));
    }

    @Test
    //Disabled
    @DisplayName("Tests for exception with BADLINKS storagetype")
    void testBadLinks(){
        StorageService storage = new StorageService();
        Collection<String> res = new ArrayList<String>();
        assertThrows(EmailFinderException.class, () -> storage.storeList(StorageService.StorageType.BADLINKS, res));
    }

    @Test
    //Disabled
    @DisplayName("Tests for exception with EMAIL storagetype")
    void testEmailLinks(){
        StorageService storage = new StorageService();
        Collection<String> res = new ArrayList<String>();
        assertThrows(EmailFinderException.class, () -> storage.storeList(StorageService.StorageType.EMAIL, res));
    }

        // 15% of storeList coverage missing (exceptions)
}
