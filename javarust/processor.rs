#![crate_type = "dylib"]
use std::os::raw::c_int;

#[no_mangle]
#[allow(non_snake_case)]
pub extern fn processInt(value: i32) -> i32 {
    value.pow(8) / 5 / 2 / 5
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern fn processFloat(value: f32) -> f32 {
    value.powi(4) * 5f32 / 304.5f32
}

/// Get the number of integers inside `MyVec`.
#[no_mangle]
#[allow(non_snake_case)]
pub unsafe extern "C" fn vecLen(vec: *const Vec<c_int>) -> c_int {
    if vec.is_null() {
        return 0;
    }

    (&*vec).len() as c_int
}
