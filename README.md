lombok
@Data sẽ tự tạo các constructor cho đối tượng
@NoArgsConstructor sẽ tự tạo constructor không có tham số
@AllArgsConstructor sẽ tự tạo constructor có tham số

các constructor được tạo sẽ gồm get, set, toString, equals, hashCode | format: [constructor][Var name] - var name sẽ là camelCase (vd: getVarName, setVarName, toStringVarName, equalsVarName, hashCodeVarName)

//////////////////////////
dùng lệnh [mvn clean install] để làm sạch và build lại từ đầu toàn bộ


//////////////////////////
@Autowired
là cách để "nhét" code của 1 class khác vào vị trí hiện tại, nơi nó được dùng, và không cần phải static nó nữa (nếu không Autowire thì phải static)