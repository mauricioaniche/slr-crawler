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
                            Which libraries to use. Currently 'scholar', 'ieee'
  -n, --stopAt=<stopAt>     The number of the last item to be captured (note
                              that the crawler might return a bit more than
                              specified, depending on the library)
  -s, --startFrom=<startFrom>
                            The number of the first item to start (could be a
                              bit less, depending on the library)
```

## License

Apache 2.0. Feel free to use it. I do not provide any support and I should
not be considered responsible for any use of this library.