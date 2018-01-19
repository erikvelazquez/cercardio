(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DomicileDeleteController',DomicileDeleteController);

    DomicileDeleteController.$inject = ['$uibModalInstance', 'entity', 'Domicile'];

    function DomicileDeleteController($uibModalInstance, entity, Domicile) {
        var vm = this;

        vm.domicile = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Domicile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
