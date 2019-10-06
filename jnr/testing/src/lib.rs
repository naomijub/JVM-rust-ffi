use std::os::raw::{c_char,c_int};

#[repr(C)]
#[no_mangle]
#[derive(Debug)]
pub struct Value {
    a: i32,
    b: i32,
    c: i32,
}

#[no_mangle]
#[allow(non_snake_case)]
pub unsafe extern "C" fn vecMean(vec: *const Value, size: i32) -> f32 {
    if vec.is_null() {
        return 0f32;
    }

    let v =  std::slice::from_raw_parts(vec, size as usize);

    (*v).iter().map(|x| (x.a + x.b + x.c) as f32 / 3f32).sum::<f32>() / size as f32
}

#[no_mangle]
#[allow(non_snake_case)]
pub unsafe extern "C" fn puts(word: *const c_char, size: i32) -> c_int {
    if word.is_null() {
        return 0;
    }

    let v_word = String::from_utf8(
        std::slice::from_raw_parts(word, size as usize)
        .to_vec()
        .iter()
        .map(|x| *x as u8)
        .collect::<Vec<u8>>())
        .unwrap();

    println!("From Rust: {:?}", v_word);
    0
}