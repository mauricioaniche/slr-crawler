# SLR Crawler

This is a simple crawler that collects references from academic
libraries. 

* It currently supports Google Scholar, ACM Digital Library, Science Direct,
IEEE Xplore, and Springer.
* It produces a CSV with the following columns: _library,title,conference,url,author,year,citations_

## How to use it

First, build a jar:

`mvn clean package -DskipTests=true`

It creates a jar with all the dependencies in the `/target` folder. Now, just: 

```
java -jar slr-crawler-1.0-SNAPSHOT-jar-with-dependencies.jar [options]
```

All the options are:
```
Usage: <main class> [-h] [--scholar-augmented] [-b=<browser>] [-d=<storageDir>]
                    [-f=<storageFormat>] -k=<keywords> -n=<stopAt>
                    [-s=<startFrom>]
                    [--scholar-starting-year=<scholarStartingYear>]
                    [--springer-content-type=<springerContentType>]
                    [--springer-discipline=<springerDiscipline>]
                    [--springer-sub-discipline=<springerSubDiscipline>]
                    [-t=<seconds>] [-l=<libraries>[,<libraries>...]]...
  -b, --browser=<browser>   Which browser to open for the crawling. You have to
                              configure Selenium's plugin in your machine.
                              Supported 'safari', 'firefox', 'chrome'. Default:
                              'safari'
  -d, --dir=<storageDir>    Directory to store everything. Default: current
                              directory
  -f, --storageFormat=<storageFormat>
                            format to store files. Default=html. Options:
                              'html', 'json'
  -h, --help                display a help message
  -k, --keywords=<keywords> The keywords used in the search
  -l, --libraries=<libraries>[,<libraries>...]
                            Which libraries to use. Currently 'scholar',
                              'ieee', 'acm', 'sciencedirect', 'springer'.
                              Default = all of them
  -n, --stopAt=<stopAt>     The number of the last item to be captured (note
                              that the crawler might return a bit more than
                              specified, depending on the library)
  -s, --startFrom=<startFrom>
                            The number of the first item to start (could be a
                              bit less, depending on the library)
      --scholar-augmented   Augment Scholar parser to get all the information
                              (it will click at the quote button for each
                              paper. Slow!) Default=false
      --scholar-starting-year=<scholarStartingYear>
                            Starting year in Google Scholar search. 0=no
                              starting year
      --springer-content-type=<springerContentType>
                            Springer content-type (check them in the website).
                              Example: 'ConferencePaper'
      --springer-discipline=<springerDiscipline>
                            Springer discipline (check them in the website).
                              Example: 'Computer Science'
      --springer-sub-discipline=<springerSubDiscipline>
                            Springer sub-discipline (check them in the
                              website). Example: 'Software Engineering'
  -t, --time=<seconds>      The number of seconds to wait in between page
                              visits. Good to avoid libraries to block you.
                              Default = 0
```

## Examples

First 500 results for "search-based software testing" in all the libraries, 
2 seconds between visiting pages. (Safari as browser, so you have to be in a Mac).

```
-k "software engineering controlled experiment"
-n 500
-t 2
```
Search for "search-based software testing" in Google Scholar and IEEE Xplore,
the 50 first results (e.g., in Google Scholar, from page 1 to 5, as Scholar gives 
10 results per page), in Firefox.

```
-k "search-based software testing"
-l "scholar,ieee"
-n 50
-d /some/dir
-b firefox
```

Search for "search-based software testing" in Google Scholar and IEEE Xplore,
from result 10 to 50 (e.g., in Google Scholar, from page 2 to 5, as Scholar gives 
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
the 50 first results (e.g., in Google Scholar, from page 1 to 5, as Scholar gives 
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

## Caveats

* It does not collect citation numbers from Springer and ScienceDirect,
as these numbers are not available in the search page. In CSV, you will see
a -1.
* A "0" in the CSV indicates that the information was not available in the 
page.
* In Google Scholar, it only collects the name of the first author. Google
truncates large lists of authors. It also does not collect the 
name of the conference, as it is fully truncated in the web page.
* Google Scholar quickly blocks you. Use larger sleep times, and crawl it in
small chunks.
 
## License

Apache 2.0. Feel free to use it. I do not provide any support and I should
not be considered responsible for any use of this library.