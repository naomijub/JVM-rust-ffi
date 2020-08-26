// use std::os::raw::{c_char,c_int};
use edn_derive::Deserialize;
use std::ffi::CString;
use std::os::raw::c_char;

#[no_mangle]
pub unsafe extern fn hello(edn: CString) -> CString{
    // let s = CString::from_raw(edn);
    // s.into_raw()
    edn
}

#[no_mangle]
pub extern "C" fn add(a: usize, b: usize) -> usize {
    a + b
}

// #[derive(Debug, Deserialize)]
// struct Person {
//     first_name: String,
//     last_name: String,
//     age: usize,
// }

// #[no_mangle]
// pub extern "C" fn otavio(edn: *mut c_char) -> *const c_char {
//     let c_string = CString::from_raw(edn);
//     let person: Person = edn_rs::from_str(&edn).expect("bad from_str");
// }
