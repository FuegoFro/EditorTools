// SET global_top_level_a TO 1. // TODO - Make lazyglobal work
DECLARE GLOBAL global_top_level_b TO 1.
DECLARE GLOBAL global_top_level_c IS 1.
GLOBAL global_top_level_d TO 1.
GLOBAL global_top_level_e IS 1.

LOCAL local_top_level_a TO 1.
LOCAL local_top_level_b IS 1.

LOCK lock_a TO 1.

RUN Imported1.
RUNPATH("Imported2").
RUN ONCE Imported3.
RUNONCEPATH("Imported4").

FUNCTION func_a {
    DECLARE PARAMETER param_should_not_show_up_1.
    PARAMETER param_should_not_show_up_2.

    LOCAL local_should_not_show_up TO 1.
    // Make sure this isn't interpreted as global
    SET local_should_not_show_up TO 1.
    IF TRUE {
        SET local_should_not_show_up TO 1.
    }

    FUNCTION func_default_visibility_should_not_show_up {}
    LOCAL FUNCTION func_local_should_not_show_up {}
    GLOBAL FUNCTION func_global_in_func {}

    LOCK lock_default_visibility_in_func to 1.
    LOCAL LOCK lock_local_should_not_show_up to 1.
    GLOBAL LOCK lock_global_in_func to 1.

    // SET global_in_func_a TO 1. // TODO - Make lazyglobal work
    DECLARE GLOBAL global_in_func_b TO 1.
    DECLARE GLOBAL global_in_func_c IS 1.
    GLOBAL global_in_func_d TO 1.
    GLOBAL global_in_func_e IS 1.

    RUN Imported5.
}

FUNCTION func_b {
    DECLARE PARAMETER param_a.
    PARAMETER param_b.
    PARAMETER param_c TO { TRUE. }.

    DECLARE local_a TO 1.
    DECLARE local_b IS 1.
    DECLARE LOCAL local_c TO 1.
    DECLARE LOCAL local_d IS 1.
    LOCAL local_e TO 1.
    LOCAL local_f IS 1.

    RUN Imported6.

    FROM { LOCAL from_var IS 1. }
    UNTIL from_var = 0
    STEP { SET from_var TO from_var - 1. LOCAL step_var TO from_var. }
    DO {
        FOR for_var IN fake_list {
            // TODO - Test without period once parsing can recover
            PRINT <caret>.

            LOCAL local_after_caret_should_not_show_up IS 1.
            GLOBAL global_after_caret IS 1.
        }
    }

    LOCAL local_after_block_should_not_show_up IS 1.
}

LOCAL local_after_function IS 1.

FUNCTION global_func_after {}
