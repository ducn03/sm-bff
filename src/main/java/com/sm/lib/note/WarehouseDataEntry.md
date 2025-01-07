## Thông tin chung

> __**Ghi chú:**__ 
> - Có 4 phần chính sản phầm (product), nguyên liệu (material), kho (Storage), import và export
> - Mỗi tiêu đề có đánh dánh dấu 1 cái key : [title] - [key]
> - Kế thừa thuộc tính có nghĩa là thuộc tính ở bảng này cộng (+) với thuộc tính của bảng khác


### Thông tin cơ bản cho các thực thể (Entity) (Sử dụng gần như tất cả mọi nơi) - BaseEntity
| Tên field | Mô tả         | Ghi chú                                       | 
|-----------|---------------|-----------------------------------------------|
| status    | Trạng thái    | dropdown với 2 trạng thái active, inactive    |
| createdAt | Ngày khởi tạo | Tự động tính toán, chỉ hiển thị khi cần thiết |
| updatedAt | Ngày cập nhật | Tự động tính toán, chỉ hiển thị khi cần thiết |

### Thông tin cơ bản mở rộng (Hiện tại sử dụng trên thông tin của sản phẩm, và nguyên liệu, có kế thừa thông tin từ BaseEntity) - BaseExtraDataEntity
| Tên field        | Mô tả                                             | ghi chú             |
|------------------|---------------------------------------------------|---------------------|
| mainId           | id của sản phẩm, hoặc nguyên liệu (dùng chung)    | Không hiển thị      |
| attributeName    | Tên thuộc tính (Màu sắc, kích thước, trọng lượng) | Nhập, hoặc dropdown |
| attributeValue   | giá trị của thuộc tính (Đỏ, L, 500)               | Nhập, hoặc dropdown |
| attributeGroupId | Nhóm thuộc tính (Cơ bản, ...)                     | dropdown            |
| description      | Mô tả chi tiết về thuộc  tính này                 | textarea            |
> __**Ghi chú:**__
>- Quan hệ 1-n (1 - nhiều)
>- 1 sản phẩm có rất nhiều thuộc tính
>- 1 nguyên liệu có rất nhiều thuộc tính
>- Và khi 1 bảng ví dụ product kế thừa BaseExtraDataEntity thì sẽ là thông tin thuộc tính của chính nó (product + BaseExtraDataEntity + BaseEntity), tức là có bao nhiêu field thì cộng lại hết á

> __**Ví dụ:**__ Nguyên liệu bún - có trọng lượng 2kg - và kích thước sợi nhỏ

### Thông tin chung cơ bản khi xuất nhập kho - TransferBaseMaterialItemEntity - có kế thừa BaseEntity
| Tên field   | Mô tả                                                  | ghi chú                     |
|-------------|--------------------------------------------------------|-----------------------------|
| materialId  | id của nguyên liệu                                     | Không hiển thị              |
| amount      | số lượng. có thể có số lẻ. ví dụ 1/2 thùng bia         | Nhập                        |
| type        | loại nhập liệu: automatic (tự động), manual (thủ công) | radio hoặc dropdown         |
| requesterId | Người nhập liệu                                        | tự động, nhưng vẫn hiển thị |
| approveId   | Người xét duyệt                                        | tự động, nhưng vẫn hiển thị |
| note        | ghi chú nếu có                                         | textarea                    |

## Thông tin riêng

### Về sản phẩm
### 1. Thông tin cơ bản của sản phẩm - product (có kế thừa thuộc tính BaseEntity)
| Tên field           | Mô tả                                                                               | ghi chú              |
|---------------------|-------------------------------------------------------------------------------------|----------------------|
| brandId             | Tên thương hiệu                                                                     | dropdown             |
| name                | Tên sản phẩm (Cháo gà, Gỏi thịt, ...)                                               | Nhập                 |
| price               | Giá cơ bản của sản phẩm                                                             | Nhập                 |
| saleOffPrice        | Giá giảm sản phẩm theo giá vnd                                                      | Nhập                 |
| saleOffPricePercent | Giá giảm sản phẩm theo %                                                            | Nhập                 |
| imageUrl            | Chỗ để thêm ảnh                                                                     | upload file          |
| description         | Mô tả của sản phẩm                                                                  | textarea             |
| categoryId          | Danh mục sản phẩm                                                                   | dropdown             |
| order               | Số thứ tự của sản phẩm. Ví dụ p1 có order = 2, p2 có order = 1. Thì p2 xếp trước p1 | nhập, chưa dùng tới  |
| isSet               | Sản phẩm có phải combo không                                                        | radio, Chưa dùng tới |
| type                | loại sản phẩm                                                                       | Chưa dùng tới        |
| repeatTime          | Thời gian lặp lại                                                                   | Chưa dùng tới        |

### 2. Thông tin các thuộc tính của sản phẩm - ProductData (không có thêm field mới nào nhưng - kế thừa BaseExtraDataEntity)
> Giờ ta có: 1 sản phẩm A giá xxx vnđ - và n (nhiều) ProductData - Màu sắc + kích thước + trọng lượng

### 3. Thông tin các nguyên liệu (material) cấu thành sản phẩm (product) - ProductIngredient - Có kế thừa BaseEntity
| Tên field  | Mô tả                                                                                           | Ghi chú                  |
|------------|-------------------------------------------------------------------------------------------------|--------------------------|
| productId  | Thông tin sản phẩm muốn cấu thành (Chỉ là định danh để biết nguyên liệu này thuộc sản phẩm nào) | Tự động, không hiển thị  |
| materialId | Nguyên liệu để cấu thành sản phẩm. Ví dụ con gà                                                 | dropdown                 |
| amount     | số lượng. có thể có số lẻ. ví dụ 1/2 con gà                                                     | Nhập                     |
> Giờ ta có: 
> - 1 sản phẩm A giá xxx vnđ
> - và n (nhiều) ProductData - Màu sắc + kích thước + trọng lượng
> - và n (nhiều) ProductIngredient - 1/2 con gà + ? muối + ? ớt (nguyên liệu để cấu thành 1 sản phẩm)


### Về nguyên liệu
### 1. Thông tin về nguyên liệu - MaterialItem (Không có kế thừa)
| Tên field    | Mô tả                                       | Ghi chú  |
|--------------|---------------------------------------------|----------|
| materialName | Tên nguyên liệu                             | Nhập     |
| materialType | Loại nguyên liệu                            | dropdown |
| description  | Mô tả chi tiết về nguyên liệu               | textarea |
| unitId       | Đơn vị của nguyên liệu. Ví dụ kg, cm, ml, L | dropdown |
| companyId    | Tên công ty (nguyên liệu của cty nào)       | dropdown |

### 2. Thông tin về thuộc tính của nguyên liệu - MaterialData (không có field nào mới) (có kế thừa BaseExtraDataEntity)
> Giờ ta có: 1 nguyên liệu A có đơn vị ... - và n (nhiều) MaterialData - Màu sắc + kích thước + trọng lượng

### Về kho
### 1. Thông tin cơ bản về kho - Storage (Có kế thừa BaseEntity)
| Tên field   | Mô tả                     | Ghi chú  |
|-------------|---------------------------|----------|
| companyId   | Tên công ty               | dropdown |
| branchId    | Tên chi nhánh của công ty | dropdown |
| locationId  | Vị trí của kho            | dropdown |
| description | Mô tả chi tiết nếu có     | textarea |

### 2. Thông tin về kho - StorageItem (Có kế thùa BaseEntity)
| Tên field      | Mô tả                                 | Ghi chú                    |
|----------------|---------------------------------------|----------------------------|
| materialItemId | Tên nguyên liệu                       | dropdown                   |
| totalQuantity  | Tổng số lượng của nguyên liệu         | nhập, và tự động tính toán |
| remainQuantity | Tổng số lượng còn lại của nguyên liệu | nhập và tự động tính toán  |

> Giờ ta có: Thông tin kho và thông tin nguyên liệu có trong kho
> - 1 Kho của cty A, chi nhánh A, vị trí A và 
> - n (nhiều) StorageItem gồm 
> - - nguyên liệu A có tổng số lượng nguyên liệu và nguyên liệu còn lại
> - - nguyên liệu B có tổng số lượng nguyên liệu và nguyên liệu còn lại
> - ...

### về import và export
### 1. Thông tin hàng hóa nhập kho mỗi lần - Importment (Có kế thừa BaseEntity)
| Tên field       | Mô tả                         | Ghi chú                |
|-----------------|-------------------------------|------------------------|
| storageId       | Tên kho                       | dropdown, hoặc tự động |
| totalMaterialId | Tổng số nguyên liệu trong kho | nhập                   |
| importerId      | Người nhập kho                | dropdown, hoặc tự động |
| approveId       | Người duyệt nhập kho          | dropdown, hoặc tự động |

### 2. Thông tin hàng hóa chi tiết mỗi lần nhập kho - ImportmentItem (Có kế thừa BaseEntity)
| Tên field  | Mô tả                                          | Ghi chú                |
|------------|------------------------------------------------|------------------------|
| materialId | Tên nguyên liệu                                | dropdown, hoặc tự động |
| amount     | số lượng. có thể có số lẻ. ví dụ 1/2 thùng bia | nhập                   |
| price      | Giá nhập của nguyên liệu                       | nhập                   |
| note       | Ghi chú nếu có                                 | textarea               |

> Giờ ta có: 
> - Importment có n (nhiều) ImportmentItem
> - Nhân viên A nhập nguyên liệu kho A, người duyệt B, tổng nguyên liệu nhập vào kho
> - Trong lần nhập liệu này có những nguyên liệu nào giá ra sao, vì cùng 1 nguyên liệu nhưng giá hôm nay khác ngày mai

### 3. Thông tin hàng hóa nhập kho mỗi lần - DepositProductItem (Có kế thừa TransferBaseMaterialItemEntity)
### 4. Thông tin hàng hóa khi xuất kho - WithdrawProductItem (Có kế thừa TransferBaseMaterialItemEntity)
| Tên field | Mô tả           | Ghi chú |
|-----------|-----------------|---------|
| price     | Giá khi xuất ra | Nhập    |


## IMPORTMENT
### Thông tin hàng hóa nhập kho mỗi lần
> - Tên kho
> - Tổng số nguyên liệu trong kho
> - Người nhập kho
> - Người duyệt nhập kho
> - Ngày nhập kho
> - Trạng thái
### Thông tin hàng hóa chi tiết mỗi lần nhập kho
> - Tên nguyên liệu
> - số lượng. có thể có số lẻ. ví dụ 1/2 thùng bia
> - Giá nhập của nguyên liệu
> - Ghi chú nếu có