#/bin/bash

PATH_TO_CD=./
DESTINATION=./
PIXEL_SIZE=1260x60

find $PATH_TO_CD -name "*.jpg" > ~/list.txt;
while read i ;
do
FILE=`basename "$i"`;
echo "Converting $i" ;
convert -resize $PIXEL_SIZE -quality 100 "$i" "$DESTINATION/$FILE" ;
done < ~/list.txt
echo "--------- FINISHED ---------"
