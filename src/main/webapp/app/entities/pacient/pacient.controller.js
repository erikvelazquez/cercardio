(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientController', PacientController);

    PacientController.$inject = ['Pacient', 'PacientSearch', 'NgTableParams', 'TAMANIOS_PAGINA'];

    function PacientController(Pacient, PacientSearch, NgTableParams, TAMANIOS_PAGINA) {

       
        var vm = this;
        
                vm.pacients = [];
                vm.detalle = clear;
                vm.clear = clear;
                vm.search = search;
                vm.loadAll = loadAll;
                vm.pacientselected = {};
                loadAll();
        
                function loadAll() {
                    Pacient.query(function(result) {
                        vm.pacients = result;
                        configData();
                        //self.tableParams = new NgTableParams({ count: 5 }, { counts: [5, 10, 20], dataset: simpleList});
                        vm.searchQuery = null;
                    });
                }
        
                function search() {
                    if (!vm.searchQuery) {
                        return vm.loadAll();
                    }
                    PacientSearch.query({query: vm.searchQuery}, function(result) {
                        vm.pacients = result;
                        configData();
                        vm.currentSearch = vm.searchQuery;
                    });
                }
        
                function clear() {
                    vm.searchQuery = null;
                    loadAll();
                }       
                
                function configData(){
                    vm.defaultConfigTableParams = new NgTableParams({ count: 5 }, { counts: TAMANIOS_PAGINA, dataset: vm.pacients});
                }
        
                vm.getData = function(item){           
                    vm.pacientselected = item;
        
                    
                };
    }
})();
