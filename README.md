# SLR Crawler

This is a simple crawler that collects references from academic
libraries, such as Google Scholar and IEEE Xplore.

```
Usage: <main class> [-h] [-b=<browser>] -d=<storageDir> [-f=<storageFormat>]
                    -k=<keywords> -n=<stopAt> [-s=<startFrom>] -l=<libraries>[,
                    <libraries>...] [-l=<libraries>[,<libraries>...]]...
  -b, --browser=<browser>   Which browser to open for the crawling. You have to
                              configure Selenium's plugin in your machine.
                              Supported 'safari', 'firefox', 'chrome'
  -d, --dir=<storageDir>    Directory to store everything
  -f, --storageFormat=<storageFormat>
                            format to store files. Default=html. Options:
                              'html', 'json'
  -h, --help                display a help message
  -k, --keywords=<keywords> The keywords used in the search
  -l, --libraries=<libraries>[,<libraries>...]
                            Which libraries to use. Currently 'scholar', 'ieee', 
                              'acm'
  -n, --stopAt=<stopAt>     The number of the last item to be captured (note
                              that the crawler might return a bit more than
                              specified, depending on the library)
  -s, --startFrom=<startFrom>
                            The number of the first item to start (could be a
                              bit less, depending on the library)
```

## Examples

Search for "search-based software testing" in Google Scholar and IEEE Xplore,
the 50 first results (e.g., in Google Scholar, from page 0 to 5, as Scholar gives 
10 results per page).

```
-k "search-based software testing"
-l "scholar,ieee"
-n 50
-d /some/dir
-b safari
```

Search for "search-based software testing" in Google Scholar and IEEE Xplore,
from result 10 to 50 (e.g., in Google Scholar, from page 1 to 5, as Scholar gives 
10 results per page).

```
-k "search-based software testing"
-l "scholar,ieee"
-s 10
-n 50
-d /some/dir
-b safari
```

Search for "search-based software testing" in Springer,
the 50 first results (e.g., in Google Scholar, from page 0 to 5, as Scholar gives 
10 results per page) only in Computer Science -> Software Engineering, Conference papers.

```
-k "search-based software testing"
-l "springer"
-n 50
-d /some/dir
-b safari
--springer-discipline "Computer Science"
--springer-discipline "Software Engineering"
--springer-content-type "ConferencePaper"
```

## Selenium

This tool uses Selenium to visit the webpages. Selenium opens the browser
in your machine. Unfortunately, HtmlUnit does not work for some of the websites;
it has to be a real browser.

* If you are using Mac, just go for "Safari", and everything will work.
* If you are not using Mac, go for "firefox" or "chrome". For Chrome,
you have to download the [ChromeDriver](https://sites.google.com/a/chromium.org/chromedriver/downloads)
and set _webdriver.chrome.driver_ global path to the Chrome Driver. Check
Selenium's documentation on how to make it work in your platform.  

## License

Apache 2.0. Feel free to use it. I do not provide any support and I should
not be considered responsible for any use of this library.