export interface IRI {
  localName: string;
  namespace: string;
  iri: boolean;
  resource: boolean;
  bnode: boolean;
  literal: boolean;
  triple: boolean;
}
