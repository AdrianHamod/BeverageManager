export interface RegisterUserModel {
  emailAddress: string;
  firstName: string;
  lastName: string;
  password: string;
  age: number;
  gender: string;
  country: { name: string, code: string };
}
