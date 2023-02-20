package finance

import (
	"fmt"
	"testing"
)

// TestEncryptAES 此函数用来进行私钥加密，建议自定义加密 KEY
// 用法: 执行 TestEncryptAES 并将控制台加密数据覆写到 .env
func TestEncryptAES(t *testing.T) {
	// plaintext 私钥地址
	pt := "This is a secret!"
	s, err := EncryptAES([]byte(KEY), pt)
	CheckError(err)
	fmt.Println(s)
}

func TestReadClear(t *testing.T) {
	privateKeyHex := ReadClear(".env")
	fmt.Println("Read from file: ", privateKeyHex)
}

func TestReadPrivateKey(t *testing.T) {
	// plaintext 私钥地址
	privateKey := ReadPrivateKey(".env")
	fmt.Println(privateKey)
}

func TestDE(t *testing.T) {
	// plaintext
	pt := "This is a secret!"
	fmt.Println(pt)
	// ciphertext
	c, err := EncryptAES([]byte(KEY), pt)
	CheckError(err)
	fmt.Println(c)
	// decrypt
	s, err := DecryptAES([]byte(KEY), c)
	CheckError(err)
	fmt.Println("DECRYPTED:", s)
}
