(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DrugAddictionDeleteController',DrugAddictionDeleteController);

    DrugAddictionDeleteController.$inject = ['$uibModalInstance', 'entity', 'DrugAddiction'];

    function DrugAddictionDeleteController($uibModalInstance, entity, DrugAddiction) {
        var vm = this;

        vm.drugAddiction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DrugAddiction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
