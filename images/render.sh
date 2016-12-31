convert \
    -background '#00000000' \
    -size 770x70 \
    -font "/System/Library/Fonts/Hiragino Sans GB W6.ttc" \
    -pointsize 24 \
    -fill white \
    -gravity center \
    label:@$1.txt \
    $1.png
