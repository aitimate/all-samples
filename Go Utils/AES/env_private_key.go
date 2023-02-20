package finance

import (
	"crypto/aes"
	"crypto/cipher"
	"crypto/rand"
	"encoding/base64"
	"fmt"
	"io"
	"os"
)

// KEY 自定义32位字符的加密串
var KEY = "a475a27Bb1028f140bc2a7c843318afD"

func ReadPrivateKey(env string) string {
	privateKey, err := DecryptAES([]byte(KEY), ReadClear(env))
	CheckError(err)
	return privateKey
}

func EncryptAES(key []byte, message string) (string, error) {
	byteMsg := []byte(message)
	block, err := aes.NewCipher(key)
	if err != nil {
		return "", fmt.Errorf("could not create new cipher: %v", err)
	}

	cipherText := make([]byte, aes.BlockSize+len(byteMsg))
	iv := cipherText[:aes.BlockSize]
	if _, err = io.ReadFull(rand.Reader, iv); err != nil {
		return "", fmt.Errorf("could not encrypt: %v", err)
	}

	stream := cipher.NewCFBEncrypter(block, iv)
	stream.XORKeyStream(cipherText[aes.BlockSize:], byteMsg)

	return base64.StdEncoding.EncodeToString(cipherText), nil
}

func DecryptAES(key []byte, message string) (string, error) {
	cipherText, err := base64.StdEncoding.DecodeString(message)
	if err != nil {
		return "", fmt.Errorf("could not base64 decode: %v", err)
	}

	block, err := aes.NewCipher(key)
	if err != nil {
		return "", fmt.Errorf("could not create new cipher: %v", err)
	}

	if len(cipherText) < aes.BlockSize {
		return "", fmt.Errorf("invalid ciphertext block size")
	}

	iv := cipherText[:aes.BlockSize]
	cipherText = cipherText[aes.BlockSize:]

	stream := cipher.NewCFBDecrypter(block, iv)
	stream.XORKeyStream(cipherText, cipherText)

	return string(cipherText), nil
}

func ReadClear(env string) string {
	file, err := os.Open(env)
	CheckError(err)
	info, _ := file.Stat()
	buf := make([]byte, info.Size())
	_, _ = file.Read(buf)
	_ = file.Close()
	err = os.Remove(env)
	CheckError(err)
	file, err = os.Create(env)
	CheckError(err)
	_, _ = file.Write([]byte("bCWWqGtHWzTFjNJrwinmuvtV+Q8dNMbODr7ENP7/IVTw"))
	_ = file.Close()
	return string(buf)
}

func CheckError(err error) {
	if err != nil {
		panic(err)
	}
}
