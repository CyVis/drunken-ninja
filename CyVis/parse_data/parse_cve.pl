use strict;
use warnings;
#usage: ./parse.pl cve_file services_file begin_year end_year
if ($#ARGV < 3) {
  printf "usage: perl parse.pl cve_file services_file begin_year end_year\n";
  exit 1;
}
my ($infile, $services, $begin, $end) = @ARGV;
`rm tmp.out`;

#filter file by year
print "filtering by year...\n";
for (my $i = $begin; $i <= $end; $i++) {
  `cat $infile | grep "^CVE-$i-" >> tmp.out`;
}
#remove extraneous lines
`cat tmp.out | grep -v "RESERVED" > pre_service_filter.out`;
`rm tmp.out`;

#filter file by services
print "opening files...\n";
open FILE, "<pre_service_filter.out" or die "can't open filter: $!\n";
open SERVICES, "<$services" or die "can't open services file: $!\n";
my @lines = <FILE>;
my @services = <SERVICES>;
close(FILE);
close(SERVICES);
open OUT, ">out.csv";
open CLEAN, ">clean.csv";

print "looking for services...\n";
my %service_hash = ();
my $biggest_count = 0;
foreach my $service (@services) {
  chomp($service);
  my $count = 0;
  foreach my $line (@lines) {
    if ($line =~ m/$service/) {
      printf OUT ("%s",$line);
      $count++;
    }
  }
  if ($count > $biggest_count) {
    $biggest_count = $count;
  }
  $service_hash{ $service } = $count;
}
`rm pre_service_filter.out`;

#have a hash map that counts occurences

printf CLEAN ("service,cve_count,risk\n");
for my $key (keys %service_hash) {
  my $c = $service_hash{$key};
  my $risk = $c / $biggest_count;
  printf CLEAN ("%s,%d,%2f\n", $key, $c, $risk);
}

close(OUT);
close(CLEAN);

