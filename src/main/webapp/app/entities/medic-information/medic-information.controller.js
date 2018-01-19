(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Medic_InformationController', Medic_InformationController);

    Medic_InformationController.$inject = ['Medic_Information', 'Medic_InformationSearch'];

    function Medic_InformationController(Medic_Information, Medic_InformationSearch) {

        var vm = this;

        vm.medic_Informations = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Medic_Information.query(function(result) {
                vm.medic_Informations = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            Medic_InformationSearch.query({query: vm.searchQuery}, function(result) {
                vm.medic_Informations = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
