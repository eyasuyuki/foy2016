#!/usr/bin/env bash
convert \
    -background '#00000000' \
    -size 100x200 \
    -font "/System/Library/Fonts/Hiragino Sans GB W6.ttc" \
    -pointsize 96 \
    -fill white \
    -gravity Center \
    label:@$1.txt \
    x.png && \
convert \
    -crop 100x100+0+0 \
    x.png \
    $1.png
