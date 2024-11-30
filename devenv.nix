{ pkgs, ... }:
{
  dotenv.enable = true;

  languages = {
    javascript = {
      enable = true;
      npm.enable = true;
    };
  };
}
