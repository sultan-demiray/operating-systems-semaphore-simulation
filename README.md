Java Semaphore Hospital Simulation

Bu proje Java kullanılarak Semaphore yapısı ile bir hastane acil servisinin
öncelikli hasta kabul ve muayene sürecinin simülasyonunu içermektedir.

Proje Özellikleri

Çoklu thread yapısı ile hasta simülasyonu

Acil ve normal hasta önceliklendirmesi

Semaphore kullanımı ile sınırlı muayene odası kontrolü

Mutex ile kritik bölge yönetimi

Merkezi planlayıcı (scheduler) thread yapısı

Kullanılan Yapılar

Java Thread

Semaphore & Mutex

Senkronizasyon mekanizmaları

Öncelikli kuyruk yönetimi

Çalışma Mantığı

Hastalar acil veya normal olarak sisteme giriş yapar

Acil hastalar her zaman öncelikli olarak muayeneye alınır

Aynı anda en fazla belirli sayıda hasta muayene edilebilir

Planlayıcı thread hastaları uygun sırayla muayeneye yönlendirir

Tüm hastalar tamamlandığında sistem güvenli şekilde kapanır

Çıktılar

Konsol üzerinden hasta akış ve durum logları

Sistem sonunda tüm hastaların tamamlandığını gösteren bilgilendirme


Not:
Bu proje, İşletim Sistemleri / Eşzamanlı Programlama konularında Semaphore kullanımını ve öncelikli planlamayı pekiştirmek amacıyla geliştirilmiştir.
