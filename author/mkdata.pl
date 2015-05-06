#!/usr/bin/env perl
use strict;
use warnings;
use utf8;
use 5.010000;
use autodie;

use CDB_File;
use File::ShareDir 'dist_file';

use Geo::Coordinates::Converter::iArea;

say "package me.geso.geoutils.iarea.data;";
say "";
say "public class MeshCode2AreaCode {";
say "\tpublic String getAreaCode(String meshCode) {";
say "\t\tswitch (meshCode) {";
my $file = dist_file('Geo-Coordinates-Converter-iArea', 'meshcode2areacode.cdb');
my %cdb;
tie %cdb, 'CDB_File', $file or die "tie failed: $!\n";
for my $key (sort keys %cdb) {
    next if $key eq '0';
    say "\t\tcase \"$key\":";
    say "\t\t\treturn \"$cdb{$key}\";";
}
say "\t\tdefault:";
say "\t\t\treturn null;";
say "\t\t} // switch";
say "\t} // method";
say "} // class";

