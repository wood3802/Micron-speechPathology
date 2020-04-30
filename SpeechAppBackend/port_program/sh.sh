#!/bin/sh
oldsuffix="sph"
newsuffix="wav"
dir=$(eval pwd)

for file in $(ls $dir | grep .${oldsuffix})
    do
        name=$(ls ${file} | cut -d. -f1)
        mv $file ${name}.${newsuffix}
    done
echo "change sph to wav successd!"
