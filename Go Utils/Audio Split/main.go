package main

import (
	"fmt"
	"github.com/google/logger"
	ffmpeg "github.com/u2takey/ffmpeg-go"
	"path/filepath"
	"strings"
)

func InFormat1(target string, str_array []string) bool {
	for _, element := range str_array {
		if target == element {
			return true
		}
	}
	return false
}

// 提取MP3
func ExtractMp3(inputFile, outputDir string) string {

	_, file := filepath.Split(inputFile)
	index := strings.LastIndex(file, ".")
	output := filepath.Join(outputDir, fmt.Sprintf("%s.%s", file[:index], "mp3"))
	err := ffmpeg.
		Input(inputFile).
		Output(output, ffmpeg.KwArgs{"acodec": "libmp3lame"}).
		OverWriteOutput().ErrorToStdOut().Run()
	if err != nil {
		logger.Fatal(err)
	}
	return output
}

func main() {
	_extractMp3 := ExtractMp3("D:\\【TED-ED 700集】B站最好英语合集，刷一遍绝对能让你英语口语起飞！ - 076 - Can you solve the multiplying rabbits riddle - Alex Gendler.mp4", "D:\\")
	fmt.Println(_extractMp3)
}
