
This program takes the CVE .csv file and parses it down to two files:
  - clean.csv, a csv of Services, #vulnerabilities in CVE list, and Risk
      - risk is simply #vulnerabilities / max #vulnerabilites of all services
  - out.csv, a csv of all vulnerabilities for specified services in time range


usage:

    perl parse_cve.pl cve_file.csv services_file.txt begin_year end_year

example:

    perl parse_cve.pl allitems.csv services.txt 2013 2013

  This pulls out only the most recent year and prints to 'out.csv' vulnerabilities found in that time range

  It also creates the clean.csv file


Services:

  the services file is a text file with services listed one per line
