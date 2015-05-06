#!/usr/bin/env perl
use strict;
use warnings;
use utf8;
use 5.010000;
use autodie;
use Text::MicroTemplate::File;
use FindBin;

package IArea {
    use Moo;

    has 'area_id' => (is => 'rw');
    has 'sub_area_id' => (is => 'rw');
    has 'area_name' => (is => 'rw');
    has 'most_w' => (is => 'rw');
    has 'most_s' => (is => 'rw');
    has 'most_e' => (is => 'rw');
    has 'most_n' => (is => 'rw');

    sub set_meshs { $_[0]->{meshs} = $_[1] }
    sub meshs { shift->{meshs} }
}

&main; exit;

sub main {
    my @iareas = map { parse(slurp($_)) } glob("iareadata/*.txt");
    my $mtf = Text::MicroTemplate::File->new(
        include_path => [ $FindBin::Bin ],
        use_cache    => 1,
    );
    spew('src/main/java/me/geso/geoutils/iarea/data/MeshCode2AreaCode.java',
        $mtf->render_file('MeshCode2AreaCode.java.mt', \@iareas));
}

sub parse {
    my $txt = shift;

    my ($area_id, $sub_area_id, $area_name,
        $most_w, $most_s, $most_e, $most_n,
        $m, $n, $o, $p,$q,$r,$s,
        @mesh) = split /,/, $txt;

    my $iarea = IArea->new();
    $iarea->area_id($area_id);
    $iarea->sub_area_id($sub_area_id);
    $iarea->area_name($area_name);
    $iarea->most_w($most_w);
    $iarea->most_s($most_s);
    $iarea->most_e($most_e);
    $iarea->most_n($most_n);
    # shift しまくるの､遅いけど可読性は高い｡
    $iarea->set_meshs(
        [
            [map { shift @mesh } 1..$m], # 2次メッシュ
            [map { shift @mesh } 1..$n], # 3次
            [map { shift @mesh } 1..$o],
            [map { shift @mesh } 1..$p],
            [map { shift @mesh } 1..$q],
            [map { shift @mesh } 1..$r],
            # 8次はデータなし
        ]
    );
    return $iarea;
}

sub slurp {
    my $filename = shift;
    open my $fh, '<', $filename;
    my $txt = do { local $/; <$fh> };
}

sub spew {
    my ($filename, $content) = @_;
    open my $fh, '>', $filename;
    print {$fh} $content;
}

