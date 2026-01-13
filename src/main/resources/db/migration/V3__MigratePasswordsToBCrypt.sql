-- Mark all plain text passwords as requiring reset
-- This migration identifies passwords that are not yet encrypted with BCrypt
-- and prefixes them with 'RESET_REQUIRED:' to force password reset on next login
UPDATE users 
SET password = 'RESET_REQUIRED:' || password 
WHERE password NOT LIKE '$2a$%' 
  AND password NOT LIKE 'RESET_REQUIRED:%';