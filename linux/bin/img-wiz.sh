#!/bin/bash

source bin-utils.sh

state=$HOME'/bin/img-wiz.data'
find . -maxdepth 1 -type f \( \
    -name "*.bmp" \
    -o -name "*.gif" \
    -o -name "*.jpg" \
    -o -name "*.jpeg" \
    -o -name "*.png" \
    -o -name "*.svg" \
    \) -exec basename {} \; > "$state"
while IFS='' read -r line || [[ -n "$line" ]]; do
    clear
    echo "####"
    echo "#### $line"
    echo "####"
    printf "\n"

    viewnior "$line" &
    sleep 3
    pkill viewnior

    # Must redirect to tty due to prompting inside read loop
    (   
      filename="$(choose_image_location)"
      
      # Skip bad filename
      if [ -z "$filename" ]; then
        echo "Bad filename. Skipping..."
        read -n1 -r -p "Press any key to continue..." key
        exit 1
      fi

      filename+="/$line"

      # Skip existing file
      if [ -f "$filename" ]; then
        echo $filename" already exists. Skipping..."
        read -n1 -r -p "Press any key to continue..." key
        exit 1
      fi

      mv "$line" "$filename"
    ) </dev/tty
  
    printf "\n"
done < "$state"

rm "$state"
