data "aws_availability_zones" "available" {}

data "aws_route_table" "existing_rt" {
  filter {
    name   = "vpc-id"
    values = [local.vpc_id]
  }
  filter {
    name   = "tag:Name"
    values = ["eks_rt"]
  }
}

data "aws_internet_gateway" "existing_igw" {
  filter {
    name   = "attachment.vpc-id"
    values = [local.vpc_id]
  }
}

data "aws_vpc" "existing_vpc" {
  filter {
    name   = "tag:Name"
    values = ["eks_vpc"]
  }
}

data "aws_subnet" "existing_subnet_1" {
  filter {
    name   = "vpc-id"
    values = [local.vpc_id]
  }
  filter {
    name   = "cidr-block"
    values = ["10.0.0.0/24"]
  }
}

data "aws_subnet" "existing_subnet_2" {
  filter {
    name   = "vpc-id"
    values = [local.vpc_id]
  }
  filter {
    name   = "cidr-block"
    values = ["10.0.1.0/24"]
  }
}

# end of existing data

resource "aws_vpc" "eks_vpc" {
  count      = length(data.aws_vpc.existing_vpc.id) == 0 ? 1 : 0
  cidr_block = "10.0.0.0/16"
  tags = {
    Name = "eks_vpc"
  }
}

locals {
  vpc_id = length(data.aws_vpc.existing_vpc.id) > 0 ? data.aws_vpc.existing_vpc.id : aws_vpc.eks_vpc[0].id
  cidr_block = length(data.aws_vpc.existing_vpc.id) > 0 ? data.aws_vpc.existing_vpc.cidr_block : aws_vpc.eks_vpc[0].cidr_block
}


output "vpc_id" {
  value = local.vpc_id
}

resource "aws_subnet" "eks_subnet_1" {
  count             = length(data.aws_subnet.existing_subnet_1.id) == 0 ? 1 : 0
  vpc_id            = local.vpc_id
  cidr_block        = "10.0.0.0/24" #cidrsubnet(local.cidr_block, 8, count.index)
  availability_zone = element(data.aws_availability_zones.available.names, count.index)
}

resource "aws_subnet" "eks_subnet_2" {
  count             = length(data.aws_subnet.existing_subnet_2.id) == 0 ? 1 : 0
  vpc_id            = local.vpc_id
  cidr_block        = "10.0.1.0/24" #cidrsubnet(local.cidr_block, 8, count.index)
  availability_zone = element(data.aws_availability_zones.available.names, count.index)
}
# Internet Gateway
resource "aws_internet_gateway" "igw" {
  count = length(data.aws_internet_gateway.existing_igw.id) == 0 ? 1 : 0
  vpc_id = local.vpc_id
}


# Route Table
resource "aws_route_table" "rt" {
  count = length(data.aws_route_table.existing_rt.id) == 0 ? 1 : 0
  vpc_id            = local.vpc_id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = length(data.aws_internet_gateway.existing_igw.id) > 0 ? data.aws_internet_gateway.existing_igw.id : aws_internet_gateway.igw[0].id
  }
  tags = {
    Name = "eks_rt"
  }
}

# Route Table Associations
resource "aws_route_table_association" "a" {
  subnet_id      = length(data.aws_subnet.existing_subnet_1.id) > 0 ? data.aws_subnet.existing_subnet_1.id : aws_subnet.eks_subnet_1[0].id
  route_table_id = length(data.aws_route_table.existing_rt.id) > 0 ? data.aws_route_table.existing_rt.id : aws_route_table.rt[0].id
}

resource "aws_route_table_association" "b" {
  subnet_id      = length(data.aws_subnet.existing_subnet_2.id) > 0 ? data.aws_subnet.existing_subnet_2.id : aws_subnet.eks_subnet_2[0].id
  route_table_id = length(data.aws_route_table.existing_rt.id) > 0 ? data.aws_route_table.existing_rt.id : aws_route_table.rt[0].id
}
