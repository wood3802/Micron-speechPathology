#!/bin/sh
oldsuffix="txt"
newsuffix="wav"
dir=$(eval pwd)

for file in $(ls $dir | grep .${newsuffix})
    do
        name=$(ls ${file} | cut -d. -f1)
        soxi $file > ${name}.${oldsuffix}
    done
echo "make the sox header file successd!"
