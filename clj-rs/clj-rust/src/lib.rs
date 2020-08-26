// use std::os::raw::{c_char,c_int};
use edn_derive::Deserialize;
use std::ffi::{CString, CStr};
use std::os::raw::c_char;
use std::str;
use std::mem;

#[no_mangle]
pub unsafe extern fn hello(edn: *const c_char) -> *const c_char{
    let mut s = to_string(edn);
    s.push_str(" from rust");
    to_c_char(s)

}

#[no_mangle]
pub extern "C" fn add(a: usize, b: usize) -> usize {
    a + b
}

#[derive(Debug, Deserialize)]
struct Person {
    first_name: String,
    last_name: String,
    age: usize,
}

impl Person {
    fn text(&self) -> String {
        format!("{} {} is {} years old", self.first_name, self.last_name, self.age)
    }
}

#[no_mangle]
pub extern "C" fn person_txt(edn: *const c_char) -> *const c_char {
    let edn_str = to_string(edn);
    let person: Person = edn_rs::from_str(&edn_str).expect("bad from_str");
    to_c_char(person.text())
}


fn to_string(pointer: *const c_char) -> String {
    let slice = unsafe { CStr::from_ptr(pointer).to_bytes() };
    str::from_utf8(slice).unwrap().to_string()
}

fn to_c_char(s: String) -> *const c_char {
    let cs = CString::new(s.as_bytes()).unwrap();
    let ptr = cs.as_ptr();
    mem::forget(cs);
    ptr
}