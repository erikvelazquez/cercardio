(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientContactDialogController', PacientContactDialogController);

    PacientContactDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PacientContact', 'Pacient'];

    function PacientContactDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, PacientContact, Pacient) {
        var vm = this;

        vm.pacientContact = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pacients = Pacient.query({filter: 'pacientcontact-is-null'});
        $q.all([vm.pacientContact.$promise, vm.pacients.$promise]).then(function() {
            if (!vm.pacientContact.pacient || !vm.pacientContact.pacient.id) {
                return $q.reject();
            }
            return Pacient.get({id : vm.pacientContact.pacient.id}).$promise;
        }).then(function(pacient) {
            vm.pacients.push(pacient);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pacientContact.id !== null) {
                PacientContact.update(vm.pacientContact, onSaveSuccess, onSaveError);
            } else {
                PacientContact.save(vm.pacientContact, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:pacientContactUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
