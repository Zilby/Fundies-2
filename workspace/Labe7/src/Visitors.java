interface IFunc<A, R> {
  R apply(A arg);
}

interface IPred<T> {
    boolean apply(T t);
}

interface IList<T> {
  IList<T> filter(IPred<T> pred);
  int length();
  <U> IList<U> map(IFunc<T, U> f);
  IList<T> append(IList<T> t);
  <U> IList<U> accept(IListVisitor<T, U> v);
}

class MtList<T> implements IList<T> {
    public int length() { 
        return 0; 
    }
    public <U> IList<U> map(IFunc<T, U> f) { 
        return new MtList<U>(); 
    }
    public IList<T> append(IList<T> t) {
        return t;
    }
    public IList<T> filter(IPred<T> pred) { 
        return this; 
    }
    public <U> IList<U> accept(IListVisitor<T, U> v){
        return v.visitMt(this);
    }
}

class ConsList<T> implements IList<T> {
    T first;
    IList<T> rest;
    ConsList(T first, IList<T> rest) {
      this.first = first;
      this.rest = rest;
    }
    public int length() {
        return 1 + this.rest.length();
    }
    public <U> IList<U> map(IFunc<T, U> f) {
        return new ConsList<U>(f.apply(this.first), this.rest.map(f));
    }
    public IList<T> append(IList<T> t) {
        return new ConsList<T> (this.first, this.rest.append(t));
    }
    public IList<T> filter(IPred<T> pred) { 
        if (pred.apply(this.first)) {
            return new ConsList<T>(this.first, this.rest.filter(pred));
        }
        else {
            return this.rest.filter(pred);
        }
    }
    public <U> IList<U> accept(IListVisitor<T, U> v){
        return v.visitCons(this);
    }
}

interface IListVisitor<R, T> extends IFunc<IList<R>, T> {
    IList<T> visitMt(MtList<R> m);
    IList<T> visitCons(ConsList<R> c);  
}

class MapVisitor<R, T> implements IListVisitor<R, T> {
    IFunc<IList<R>, T> f;
    MapVisitor(IFunc<IList<R>, T> f) {
      this.f = f;
    }
    public T apply(IList<R> l){
        return f.apply(l);
    }
    public IList<T> visitMt(MtList<R> m) {
        return new MtList<T>();  
    }
    public IList<T> visitCons(ConsList<R> c) {
        return new ConsList<T>(this.apply(c), c.rest.accept(this));
    }
}
