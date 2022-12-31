<h1 align="center">Đồ án tốt nghiệp - Học viện Công nghệ Bưu chính viễn thông <br/>
    Ứng dụng Android hỗ trợ bệnh nhân đăng ký khám và điều trị bệnh 
</h1>

<p align="center">
    <img src="./photo/umbrella-corporation.jpg" />
</p>

# [**Table Of Content**](#table-of-content)
- [**Table Of Content**](#table-of-content)
- [**Introduction**](#introduction)
- [**Topic**](#topic)
- [**API Document**](#api-document)
- [**Database**](#database)
- [**Directory Structure**](#directory-structure)
- [**Features**](#features)
  - [**1. Launch**](#1-launch)
  - [**2. Login**](#2-login)
  - [**3. Home**](#3-home)
  - [**4. Appointment**](#4-appointment)
  - [**5. Treatment \& Record**](#5-treatment--record)
  - [**6. Reminder**](#6-reminder)
- [**Made with 💘 and Android**](#made-with--and-android)
# [**Introduction**](#introduction)

Chào các bạn, mình tên là Nguyễn Thành Phong. 
Mã số N18DCCN147. 
Niên khóa 2018-2023. 

Lời đầu tiên mình xin chào các bạn và cảm ơn tất cả các bạn đang ở đây. Trong tài liệu này mình sẽ chia sẻ tất cả những gì các bạn cần biết khi làm đồ án 
tốt nghiệp và đề tài do mình thực hiện để các bạn có thể tham khảo. Mình hi vọng phần tài liệu mình viết tiếp theo đây 
sẽ hỗ trợ phần nào cho các bạn khi bước tới ngưỡng cửa quan trọng của cuộc đời mình - tốt nghiệp đại học.

Đồ án tốt nghiệp này có tất cả là 3 thành phần bao gồm:

* [**API**](https://github.com/Phong-Kaster/PTIT-Do-An-Tot-Nghiep) 
  
* [**Website**](https://github.com/Phong-Kaster/PTIT-Do-An-Tot-Nghiep-Website)
  
* [**Ứng dụng Android**](#) (Hiện tại)

Các bạn đang đọc phần mô tả chi tiết về `ứng dụng Android` trong đoán này trong tài liệu này mình sẽ mô tả chi tiết về cấu trúc của các thư mục các tính năng nổi bật nhất và một số những cái lưu ý khi các bạn tham khảo đồ án này.

# [**Topic**](#topic)

<p align="center">
    <img src="./photo/topic.png" />
</p>


Có thể giải thích yêu cầu đề tài ngắn gọn như sau:

**Website** - Đóng vai trò là ứng dụng quản trị viên. Hỗ trợ bệnh viện quản lý thông tin bác sĩ & bệnh nhân,
sắp xếp lịch khám bệnh giữa bác sĩ và bệnh nhân.

**Android** - Ứng dụng để bệnh nhân đặt lịch khám bệnh, theo dõi phác đồ điều trị và bệnh án của mình. Có thể đặt lịch khám bệnh
cho người thân trong gia đình như ông, bà, bố, mẹ & không nhất thiết người khám bệnh phải là bản thân mình.

Chúng ta sẽ cân phân tích đề tài kĩ hơn vì rất dễ gây nhầm lẫn. Cụ thể chính thầy hướng dẫn và thầy giáo 
phản biện đồ án của mình đã nghĩ thành 2 hướng khác nhau:

**Thầy Nguyễn Anh Hào - giáo viên hướng dẫn**: ứng dụng chỉ để bệnh nhân cung cấp thông tin cá nhân & rút ngắn thời gian khám bệnh. 
Vẫn có chức năng đặt lịch hẹn khám nhưng chỉ để cung cấp thông tin cá nhân, nhằm rút ngắn thời gian khám của bác sĩ. Hoạt động trên 
nguyên tắc `ai đến trước thì được khám trước`, không chấp nhận việc đặt giờ trả tiền trước để vào khám. Điều này ưu tiên 
những bệnh nhân nghèo, không thông thạo các thao tác trên di động, ưu tiên những người đã bỏ thời gian ra xếp hàng để khám bệnh.

**Thầy Huỳnh Trung Trụ - giáo viên phản biện**: ứng dụng là đặt lịch hẹn với bác sĩ. Tức cho chọn chuyên khoa, chọn bác sĩ khám bệnh trước & dĩ nhiên 
là chọn giờ khám luôn. Hoạt động trên nguyên tắc `tôi đặt lịch hẹn thì đúng giờ đó tui phải được vào khám`.

Như trên thì lý luận của 2 thầy đều đúng và Phong thì thiết kế chương trình theo giáo viên hướng dẫn của mình.👼👼👼
# [**API Document**](#api-document)

Mình có soạn thảo và liệt kê chi tiết cách sử dụng các chức năng mà mình đã xây dựng thành tài liệu.
Nếu các bạn có nhu cầu muốn tham khảo, hãy ấn vào [**đây**](https://github.com/Phong-Kaster/PTIT-Do-An-Tot-Nghiep-API-Document) để đọc chi tiết cách sử dụng API này.

# [**Database**](#database)

<p align="center">
    <img src="./photo/database-version-12-prototype.png" />
</p>
<h3 align="center">

***Sơ đồ cơ sở dữ liệu***
</h3>

# [**Directory Structure**](#directory-structure)

Đầu tiên mình sẽ giới thiệu về cấu trúc thư mục trong đồ án này. Hãy quan sát hình hình trụ bên dưới để có cái nhìn tổng quan nhất bật về toàn bộ cấu trúc của thư mục này

<p align="center">
    <img src="./photo/android-001.png" width="500px"/>
</p>
<p align="center">
    <img src="./photo/Android-002.png" width="500px"/>
</p>
<h3 align="center">

***Toàn bộ cấu trúc cây thư mục trong Umbrella Health - ứng dụng Android của đề tài***
</h3>

Tên của các thư mục trong dự án này được đặt tên theo quy ước như sau:

- **Thư mục Adapter** - được sử dụng để chứa các class dùng để khai báo các List View
  
- **Các thư mục có đuôi là page** - như Homepage, Loginpage,.... được sử dụng để chứa các activity hay giao diện để người sử dụng có thể để tương tác với ứng dụng 

- **Thư mục Configuration** - được sử dụng để khai báo các biến toàn cục và và các HTTP request để sử dụng API. Ứng dụng này sử dụng thư viện `Retrofit` để liên kết với các API 

- **Thư mục Container** - được sử dụng để khai báo các định dạng JSON khi  sử dụng API.

- **Thư mục Helper** - được sử dụng để khai báo các hàm hàm public static trong nhiệm vụ làm đẹp hình thức hiển thị của dữ liệu

- **Thư mục Model** - tương tự như vai trò của model trong mô hình mvc, thư mục chứa các khai báo về đối tượng xuất hiện trong phần mô tả database ở phía trên. 

- **Thư mục Recycler View** -  tập hợp tất cả các các recycler view được sử dụng trong dự án này 

- **Thư mục Repository** - là thư mục tập hợp tất cả các các khai báo để gọi các API tương ứng

- **MainActivity & MainViewModel** - sẽ là activity đầu tiên được khởi chạy khi mở ứng dụng này 

# [**Features**](#features)

Phần này mình sẽ giới thiệu về tất cả các giao diện và các chức năng chính trong ứng dụng Umbrella Health

## [**1. Launch**](#1-launch)

<p align="center">
    <img src="./photo/android-003.jpg" height="600px"/>
</p>
<h3 align="center">

***Màn hình khởi chạy khi mở ứng dụng***
</h3>

## [**2. Login**](#2-login)

<p align="center">
    <img src="./photo/android-004.jpg" height="600px"/>
</p>
<h3 align="center">

***Màn hình đăng nhập***
</h3>

Ứng dụng hỗ trợ người dùng hai tùy chọn để đăng nhập vào chương trình, bao gồm:

1. Đăng nhập bằng số điện thoại di động với mã OTP

2. Đăng nhập bằng tài khoản Google 


## [**3. Home**](#3-home)

<p align="center">
    <img src="./photo/android-005.jpg" height="600px"/>
    <img src="./photo/android-006.jpg" height="600px"/>
    <img src="./photo/android-007.jpg" height="600px"/>
</p>
<h3 align="center">

***Màn hình Home➡***
</h3>

Màn hình Home với hình ảnh minh họa bên từ trái qua là trình tự nội dung mà chúng ta sẽ nhìn thấy trên màn hình khi vuốt từ trên xuống dưới

Màn hình này bao gồm các thành phần như sau:

1. Logo
   
2. Ngày giờ 
   
3. Nhiệt độ  tại khu vực hiện tại ( OpenWeatherMap.org )

4. Thanh tìm kiếm

5. Các nút tắt để Tạo lịch hẹn với bác sĩ

6. Danh mục chuyên khoa phổ biến để hiển thị các chuyên khoa của các bác sĩ

7. Danh mục Cẩm Nang để thể hiện các bài viết

8. Danh mục tạp chí sức khỏe Hiển thị các đường dẫn tới các trang web chính thống liên quan tới sức khỏe và y tế

9. Danh mục bác sĩ nổi bật được sử dụng để hiển thị các bác sĩ đang làm việc trong bệnh viện 

10. Thanh điều hướng với 4 menu chính: Trang chủ 🏠, lịch hẹn 📆, thông báo 🔔 và cá nhân😀

## [**4. Appointment**](#4-appointment)

<p align="center">
    <img src="./photo/android-008.jpg" height="600px"/>
    <img src="./photo/android-009.jpg" height="600px"/>
</p>
<h3 align="center">

***Màn hình lịch hẹn bên phải là khi chưa có lịch hẹn và bên trái là khi đang có lịch khám với bác sĩ***
</h3>

Màn hình này sẽ hiển thị thông tin về bác sĩ, phòng khám, lý do mà bệnh nhân đăng ký khám,.....

Ngoài ra, nút **Đặt lịch hẹn** có tác dụng sẽ nhắc thông báo khi mà lượt khám của bệnh nhân sắp đến bằng cách cứ mỗi 45 giây ứng dụng sẽ gửi một HTTP Request lên hệ thống để tự động cập nhật danh sách khám bệnh hiện tại.

<p align="center">
    <img src="./photo/android-010.jpg" height="600px"/>
    <img src="./photo/android-011.jpg" height="600px"/>
</p>
<h3 align="center">

***Khi đến giờ khám của bệnh nhân ứng dụng sẽ sẽ phát thông báo và nhạc chuông để nhắc nhở người dùng***
</h3>

## [**5. Treatment & Record**](#5-treatment--record)

<p align="center">
    <img src="./photo/android-012.jpg" height="600px"/>
    <img src="./photo/android-013.jpg" height="600px"/>
</p>
<h3 align="center">

***Sau khi lập khám hoàn tất bệnh nhân có thể xem lại phác đồ điều trị và bệnh án***
</h3>

Khám hoàn tất bệnh nhân có thể mở ứng dụng để xem lại phác đồ điều trị và bệnh án của mình tương ứng với hai nút chức năng

1. Xem bệnh án
2. Xem phác đồ điều trị 

<p align="center">
    <img src="./photo/android-014.jpg" height="600px"/>
</p>
<h3 align="center">

***Màn hình xem bệnh án***
</h3>

Người dùng có thể xem phác đồ điều trị chị và có thể tự tạo nhắc nhở thực hiện toa thuốc theo hướng dẫn của bác sĩ

<p align="center">
    <img src="./photo/android-015.jpg" height="600px"/>
</p>
<h3 align="center">

***Xem phác đồ điều trị với nút Đặt nhắc nhở***
</h3>

## [**6. Reminder**](#6-reminder)

Khi người dùng nhấn vào nút **Đặt nhắc nhở**, màn hình sẽ hiện thị như hình minh họa bên dưới & người dùng có thể Tạo nhắc nhở ở tùy theo theo chỉ dẫn của bác sĩ 

<p align="center">
    <img src="./photo/android-016.jpg" height="600px"/>
</p>
<h3 align="center">

***Người dùng có thể chủ động đặt thời gian nhắc nhở 📅***
</h3>

Khi ấn vào nút **Xác nhận**,  ứng dụng sẽ tìm tới các ứng dụng trong hệ thống để tạo nhắc nhở. Nếu có nhiều hơn một ứng dụng có thể đáp ứng được yêu cầu thì hệ chương trình sẽ hiển thị ra một cửa sổ để người dùng lựa chọn  

<p align="center">
    <img src="./photo/android-017.jpg" height="600px"/>
</p>
<h3 align="center">

***Có thể lựa chọn ứng dụng 🔔🔔***
</h3>

Kho ứng dụng đồng hồ trên các mẫu điện thoại là khác nhau nên giao diện hiển thị sẽ khác nhau đôi chút nhưng chức năng thì sẽ giống nhau 

<p align="center">
    <img src="./photo/android-018.jpg" height="600px"/>
    <img src="./photo/android-019.jpg" height="600px"/>
</p>
<h3 align="center">

***Giao diện khác nhau nhưng chức năng sẽ giống nhau***
</h3>


# [**Made with 💘 and Android**](#made-with-love-and-android)